package com.shop.advance.academy.yordan.petrov.git.shop.domain.services.impl;

import com.shop.advance.academy.yordan.petrov.git.shop.data.dao.OrderDao;
import com.shop.advance.academy.yordan.petrov.git.shop.data.models.Order;
import com.shop.advance.academy.yordan.petrov.git.shop.data.models.enums.OrderStatus;
import com.shop.advance.academy.yordan.petrov.git.shop.data.models.enums.PaymentType;
import com.shop.advance.academy.yordan.petrov.git.shop.data.models.enums.ShipmentType;
import com.shop.advance.academy.yordan.petrov.git.shop.data.models.enums.TransactionStatus;
import com.shop.advance.academy.yordan.petrov.git.shop.domain.dto.OrderServiceModel;
import com.shop.advance.academy.yordan.petrov.git.shop.domain.dto.TransactionServiceModel;
import com.shop.advance.academy.yordan.petrov.git.shop.domain.dto.TransactionServiceViewModel;
import com.shop.advance.academy.yordan.petrov.git.shop.domain.services.PurchasingService;
import com.shop.advance.academy.yordan.petrov.git.shop.domain.services.TransactionService;
import com.shop.advance.academy.yordan.petrov.git.shop.exeption.IllegalCardTransactionOperation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;

/**
 * Class interface service implementation  for .
 *
 * @author Yordan Petrov
 * @version 1.0.0.0
 * @since Jul 8, 2020.
 */
@Service
public class PurchasingServiceImpl implements PurchasingService {

    private final OrderDao orderDao;
    private final ModelMapper modelMapper;
    private final TransactionService transactionService;

    /**
     * Constructor
     */
    @Autowired
    public PurchasingServiceImpl(OrderDao orderDao, ModelMapper modelMapper,
                                 TransactionService transactionService) {
        this.orderDao = orderDao;
        this.modelMapper = modelMapper;
        this.transactionService = transactionService;
    }

    /**
     * @param transactionServiceModel
     * @return
     */
    @Override
    public TransactionServiceViewModel payByCard(TransactionServiceModel transactionServiceModel) {
        Order order = findOrderFromTransactionServiceModelById(transactionServiceModel);
        isOrderStatusCanceled(order.getOrderStatus());
        isOrderStatusFinished(order.getOrderStatus());
        isOrderStatusProccesing(order.getOrderStatus());
        transactionServiceModel.setAmount(order.getTotalPrice());
        transactionServiceModel.setFee(BigDecimal.valueOf(5.00));
        transactionServiceModel.setDescription("Item purchase");
        updateOrderForPurchase(order);
        createTransactionForPayByCard(transactionServiceModel);
        //TODO ADD VIP STATIS TO CUSTOMER ON CERTAIN SPENDING AMMOUNT
        return mapTransactionServiceModelToTransactionServiceViewModel(transactionServiceModel);
    }


    /**
     * @param transactionServiceModel
     * @return
     */
    @Override
    public TransactionServiceViewModel refundCardPurchase(TransactionServiceModel transactionServiceModel) {
        TransactionServiceModel transactionServiceForRefund = getTransactionServiceModelForRefundTransaction(transactionServiceModel);
        refundCardPurchaseValidationAndUpdates(transactionServiceModel, transactionServiceForRefund);
        return mapTransactionServiceModelToTransactionServiceViewModel(transactionServiceForRefund);
    }

    /**
     * @param transactionServiceModel
     */
    private void createTransactionForPayByCard(TransactionServiceModel transactionServiceModel) {
        transactionService.createTransaction(transactionServiceModel);
    }


    /**
     * @param transactionServiceModel
     * @param transactionServiceForRefund
     */
    private void refundCardPurchaseValidationAndUpdates(TransactionServiceModel transactionServiceModel
            , TransactionServiceModel transactionServiceForRefund) {

        isTransactionStatusRefunded(transactionServiceForRefund.getTransactionStatus());
        isTimeBetweenTwoDatesGreaterOrEqualToSetDaysInSeconds(transactionServiceForRefund.getDateCompleted());
        updateOrderForRefund(findOrderFromTransactionServiceModelById(transactionServiceModel));
        transactionServiceForRefund.setTransactionStatus(TransactionStatus.REFUNDED);
        transactionServiceForRefund.setDescription("Refunded for item");
        transactionServiceForRefund.setDateUpdated(Instant.now());
        refundTransactionById(transactionServiceModel);
        updateTransactionService(transactionServiceForRefund);
    }


    /**
     * @param transactionServiceModel
     */
    private void refundTransactionById(TransactionServiceModel transactionServiceModel) {
        this.transactionService.refundTransactionById(transactionServiceModel.getId());
    }

    /**
     * @param dateTransactionCompleted
     */
    public void isTimeBetweenTwoDatesGreaterOrEqualToSetDaysInSeconds(Instant dateTransactionCompleted) {
        boolean isNonRefundable;
        //14 days in seconds
        final int daysInSecondsBetweenPurchaseAndTransaction = 1209600;
        int minutes = Duration
                .between(dateTransactionCompleted, Instant.now())
                .toSecondsPart();
        isNonRefundable = minutes >= daysInSecondsBetweenPurchaseAndTransaction;
        if (isNonRefundable) {
            throw new IllegalCardTransactionOperation("The order cannot be refunded due to 14 days have passed from the purchase till now");
        }
    }

    /**
     * @param transactionStatus
     */
    public void isTransactionStatusRefunded(TransactionStatus transactionStatus) {
        boolean isNonRefundable = false;
        isNonRefundable = transactionStatus == TransactionStatus.REFUNDED;
        if (isNonRefundable) {
            throw new IllegalCardTransactionOperation("The transaction is already refunded");
        }
    }

    /**
     * @param orderStatus
     */
    public void isOrderStatusCanceled(OrderStatus orderStatus) {
        boolean isOrderNonCanceled = false;
        isOrderNonCanceled = orderStatus == OrderStatus.CANCELED;
        if (isOrderNonCanceled) {
            throw new IllegalCardTransactionOperation("The Order is Canceled");
        }
    }

    /**
     * @param orderStatus
     */
    public void isOrderStatusFinished(OrderStatus orderStatus) {
        boolean isOrderNonCanceled = false;
        isOrderNonCanceled = orderStatus == OrderStatus.PICKED_UP_BY;
        if (isOrderNonCanceled) {
            throw new IllegalCardTransactionOperation("The Order is finished");
        }
    }

    /**
     * @param orderStatus
     */
    public void isOrderStatusProccesing(OrderStatus orderStatus) {
        boolean isOrderNonCanceled = false;
        isOrderNonCanceled = orderStatus == OrderStatus.PROCESSING;
        if (isOrderNonCanceled) {
            throw new IllegalCardTransactionOperation("The Order is proccesing");
        }
    }

    /**
     * @param transaction
     * @return
     */
    public Order findOrderFromTransactionServiceModelById(TransactionServiceModel transaction) {
        return orderDao.findById(transaction.getOrder().getId())
                .orElseThrow(() ->
                        new EntityNotFoundException("No Order have been found"));
    }

    /**
     * @param order
     */
    public void updateOrderForRefund(Order order) {
        order.setOrderStatus(OrderStatus.CANCELED);
    }

    /**
     * @param order
     */
    public void updateOrderForPurchase(Order order) {
        order.setShipmentType(ShipmentType.NONE);
        order.setPaymentType(PaymentType.BY_CARD);
        order.setOrderStatus(OrderStatus.PROCESSING);
    }

    /**
     * @param order
     * @return
     */
    public OrderServiceModel mapOrderToOrderServiceModel(Order order) {
        return this.modelMapper.map(order, OrderServiceModel.class);
    }

    /**
     * @param transactionServiceModel
     */
    public void updateTransactionService(TransactionServiceModel transactionServiceModel) {
        transactionService.updateTransaction(transactionServiceModel);
    }

    /**
     * @param transactionServiceModel
     * @return
     */
    public TransactionServiceModel getTransactionServiceModelForRefundTransaction(TransactionServiceModel transactionServiceModel) {
        return mapTransactionServiceViewModelToTransactionServiceModel(this.transactionService.getTransactionById(transactionServiceModel.getId()));
    }

    /**
     * @param transactionServiceViewModel
     * @return
     */
    public TransactionServiceModel mapTransactionServiceViewModelToTransactionServiceModel(TransactionServiceViewModel transactionServiceViewModel) {
        return this.modelMapper.map(transactionServiceViewModel, TransactionServiceModel.class);
    }

    /**
     * @param transactionServiceModel
     * @return
     */
    public TransactionServiceViewModel mapTransactionServiceModelToTransactionServiceViewModel(TransactionServiceModel transactionServiceModel) {
        return this.modelMapper.map(transactionServiceModel, TransactionServiceViewModel.class);
    }

}
