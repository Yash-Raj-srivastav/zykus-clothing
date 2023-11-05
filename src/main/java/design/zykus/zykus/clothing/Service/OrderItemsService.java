package design.zykus.zykus.clothing.Service;

import design.zykus.zykus.clothing.DAO.OrderItemsRepository;

import design.zykus.zykus.clothing.Entity.Invoice;
import design.zykus.zykus.clothing.Entity.OrderItems;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class OrderItemsService {

    @Autowired
    private OrderItemsRepository orderItemsRepository;

    public Iterable<OrderItems> getAllOrderItems() {
        return this.orderItemsRepository.findAll();
    }

    public ResponseEntity<OrderItems> getSingleOrderItems(int id){
        return this.orderItemsRepository.findById(id).
                map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    public OrderItems addNewOrderItems(OrderItems orderItems){
        return orderItemsRepository.save(orderItems);
    }

    public Iterable<OrderItems> addMultipleNewUsers(Iterable<OrderItems> multipleOrderItems){
        return orderItemsRepository.saveAll(multipleOrderItems);
    }

    public ResponseEntity<OrderItems> updateExistingOrderItems(OrderItems orderItems, int id) {
        return orderItemsRepository.findById(id)
                .map(existingOrderItems -> {
                    if (orderItems.getOrderItemStatusCode() != null) existingOrderItems.setOrderItemStatusCode(orderItems.getOrderItemStatusCode());
                    if (orderItems.getOrderItemQuantity() != 0) existingOrderItems.setOrderItemQuantity(orderItems.getOrderItemQuantity());
                    if (orderItems.getOtherOrderItemDetail() != null) existingOrderItems.setOtherOrderItemDetail(orderItems.getOtherOrderItemDetail());
                    return ResponseEntity.ok(orderItemsRepository.save(existingOrderItems));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<OrderItems> deleteExistingOrderItem(int orderItemId){
        return orderItemsRepository.findById(orderItemId)
                .map(existingOrderItem -> {
                    OrderItems deletedOrderItem = null;
                    if(existingOrderItem.getOrderItemId() == orderItemId){
                        deletedOrderItem = existingOrderItem;
                        orderItemsRepository.deleteById(orderItemId);
                    }
                    return ResponseEntity.ok(deletedOrderItem);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
