package com.cb.model;

import java.util.List;

public record Order(String orderId, List<Item> items) {
}