package com.example.janghj.service;

import com.example.janghj.repository.DeliveryRepository;
import com.example.janghj.repository.ItemRepository;
import com.example.janghj.repository.OrderRepository;
import com.example.janghj.repository.UserRepository;
import com.example.janghj.web.dto.OrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderService {
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;
    private final DeliveryRepository deliveryRepository;

    public void order(OrderDto orderDto) {
        orderDto.getOrderList().forEach((key, value) ->
                        System.out.println("key : " + key + ", value " + value));


//        Outer outer = new Outer();
//        User user = userRepository.findById(0L).orElseThrow(
//                () -> new NullPointerException("해당 사용자가 없습니다. userId = " + 0L)
//        );

//        orderDto.getOrderList().forEach((key, value) ->
////                        System.out.println("key : " + key + ", value " + value)
//                itemRepository.findById((Long)key).orElseThrow(
//                        () -> new NullPointerException("해당 아이템이 없습니다. itemId = " + key)
//                )

//        JpaRepository<Order, Long> {
//});


//                .orElseThrow(
//                    () -> new NullPointerException("해당 아이템이 없습니다. itemId = " + userDto.getUsername())
//            );


//        String orderList = orderDto.getOrderList().toString();
//        // [{itemId=3, count=2}, {itemId=1, count=4} ...]
//        String[] stringOrderData = orderList.replaceAll("[^\\d+,]", "").split(",");
//        // String 3,2,1,4...
//        int[] intOrderData = Arrays.asList(stringOrderData).stream().mapToInt(Integer::parseInt).toArray();
//        // int 3,2,1,4...
//        for (int data : intOrderData) {
//            System.out.println(data);
//        }
    }

}
