package com.github.loganag.shopApp.controllers;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.loganag.shopApp.model.Good;
import com.github.loganag.shopApp.model.Order;
import com.github.loganag.shopApp.model.OrderRequest;
import com.github.loganag.shopApp.model.Status;
import com.github.loganag.shopApp.service.GoodServiceImpl;
import com.github.loganag.shopApp.service.OrderServiceImpl;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class MainControllerTests {

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @MockBean private GoodServiceImpl goodService;

  @MockBean private OrderServiceImpl orderService;

  @Test
  @WithMockUser(username = "user", roles = "USER")
  public void testGetAllGoods() throws Exception {
    Good good1 = new Good(1L, "Product 1", 10, 25.0, new HashSet<>());
    Good good2 = new Good(2L, "Product 2", 15, 30.0, new HashSet<>());
    List<Good> goods = Arrays.asList(good1, good2);

    when(goodService.findAll()).thenReturn(goods);

    mockMvc
        .perform(MockMvcRequestBuilders.get("/order/all"))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1L))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Product 1"))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].quantity").value(10))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].price").value(25.0))
        .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2L))
        .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Product 2"))
        .andExpect(MockMvcResultMatchers.jsonPath("$[1].quantity").value(15))
        .andExpect(MockMvcResultMatchers.jsonPath("$[1].price").value(30.0));
  }

  @Test
  @WithMockUser(username = "manager", roles = "MANAGER")
  public void testAddGood() throws Exception {
    Good good = new Good(1L, "Product 1", 10, 25.0, new HashSet<>());

    when(goodService.saveGood(good)).thenReturn(1L);

    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/order/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(good)))
        .andExpect(status().isOk());
  }

  @Test
  @WithMockUser(username = "user", roles = "USER")
  public void testPlaceOrder_SuccessfulOrder() throws Exception {
    OrderRequest orderRequest = new OrderRequest();

    ObjectMapper objectMapper = new ObjectMapper();
    String orderRequestJson = objectMapper.writeValueAsString(orderRequest);

    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/order/place")
                .contentType(MediaType.APPLICATION_JSON)
                .content(orderRequestJson))
        .andExpect(status().isOk())
        .andExpect(content().string("Order placed successfully"))
        .andReturn();
  }

  @Test
  @WithMockUser(username = "manager", roles = "MANAGER")
  public void testMarkOrderAsPaid_OrderFoundAndNotPaid_ShouldMarkAsPaid() throws Exception {
    Order order = new Order();
    order.setId(1L);
    order.setStatus(Status.PENDING);

    when(orderService.findById(1L)).thenReturn(Optional.of(order));
    when(orderService.save(order)).thenReturn(order);

    mockMvc
        .perform(
            MockMvcRequestBuilders.put("/order/1/markAsPaid")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string("Order marked as paid"));

    verify(orderService, times(1)).findById(1L);
    verify(orderService, times(1)).save(order);
  }

  @Test
  @WithMockUser(username = "manager", roles = "MANAGER")
  public void testMarkOrderAsPaid_OrderFoundAndAlreadyPaid_ShouldReturnBadRequest()
      throws Exception {
    Order order = new Order();
    order.setId(2L);
    order.setStatus(Status.PAID);

    when(orderService.findById(2L)).thenReturn(Optional.of(order));

    mockMvc
        .perform(
            MockMvcRequestBuilders.put("/order/2/markAsPaid")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(content().string("Order is already paid"));

    verify(orderService, times(1)).findById(2L);
    verify(orderService, never()).save(order);
  }

  @Test
  @WithMockUser(username = "manager", roles = "MANAGER")
  public void testMarkOrderAsPaid_OrderNotFound_ShouldReturnNotFound() throws Exception {
    when(orderService.findById(3L)).thenReturn(Optional.empty());

    mockMvc
        .perform(
            MockMvcRequestBuilders.put("/order/3/markAsPaid")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Order not found"));

    verify(orderService, times(1)).findById(3L);
    verify(orderService, never()).save(any(Order.class));
  }
}
