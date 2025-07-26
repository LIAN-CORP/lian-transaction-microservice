package com.lian.marketing.transactionmicroservice.domain.api.usecase;

import com.lian.marketing.transactionmicroservice.domain.api.IDetailTransactionServicePort;
import com.lian.marketing.transactionmicroservice.domain.model.DetailTransaction;
import com.lian.marketing.transactionmicroservice.domain.model.ProductTransaction;
import com.lian.marketing.transactionmicroservice.domain.spi.IDetailTransactionPersistencePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class DetailTransactionUseCase implements IDetailTransactionServicePort {

    private final IDetailTransactionPersistencePort detailTransactionPersistencePort;

    @Override
    public Mono<Void> createDetailTransaction(DetailTransaction detailTransaction, List<ProductTransaction> products) {
        products = mergeRepeatedProducts(products);
        return Flux.fromIterable(products)
                .flatMap(product -> {
                    DetailTransaction newDetailTransaction = new DetailTransaction();
                    newDetailTransaction.setTransactionId(detailTransaction.getTransactionId());
                    newDetailTransaction.setProductId(product.getId());
                    newDetailTransaction.setQuantity(product.getQuantity());
                    return detailTransactionPersistencePort.getProductPriceById(product.getId())
                            .flatMap(price -> {
                                newDetailTransaction.setUnitPrice(price);
                                return detailTransactionPersistencePort.saveDetailTransaction(newDetailTransaction);
                            });
                })
                .then();
    }

    private List<ProductTransaction> mergeRepeatedProducts(List<ProductTransaction> products) {
        return new ArrayList<>(products.stream().collect(Collectors.toMap(
                ProductTransaction::getId,
                p -> new ProductTransaction(
                        p.getId(),
                        p.getQuantity()
                ),
                (p1, p2) -> new ProductTransaction(p1.getId(), p1.getQuantity() + p2.getQuantity())
        )).values());
    }
}
