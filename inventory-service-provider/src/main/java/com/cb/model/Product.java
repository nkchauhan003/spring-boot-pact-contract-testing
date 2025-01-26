package com.cb.model;

import java.util.UUID;

public record Product(UUID productId, int quantity) {
}