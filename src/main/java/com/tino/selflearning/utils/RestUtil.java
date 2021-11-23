package com.tino.selflearning.utils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

public class RestUtil {

  private RestUtil() {}

  public static Sort buildSortOption(String sort) {
    List<Order> orders = Arrays.stream(sort.split(",")).map(item -> {
      String[] _sort = item.split(":");
      if (_sort[1].equals("desc")) {
        return new Order(Direction.DESC, _sort[0]);
      } else {
        return new Order(Direction.ASC, _sort[0]);
      }
    }).collect(Collectors.toList());
    return Sort.by(orders);
  }
}
