package com.platzi.market.persistence;

import com.platzi.market.domain.Purchase;
import com.platzi.market.domain.repository.PurchaseRepository;
import com.platzi.market.persistence.crud.CompraCrudRepository;
import com.platzi.market.persistence.entity.Compra;
import com.platzi.market.persistence.mapper.PurchaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CompraRepository implements PurchaseRepository {


    @Autowired
    CompraCrudRepository compraCrudRepository;


    @Autowired
    PurchaseMapper purchaseMapper;

    @Override
    public List<Purchase> getAll() {
        return purchaseMapper.toPurchases((List<Compra> )compraCrudRepository.findAll());
    }
    @Override
    public Optional<Purchase> getPurchase(int purchaseId) {
        return compraCrudRepository.findById(purchaseId)
                .map(compra -> purchaseMapper.toPurchase(compra));
    }
    @Override
    public Optional<List<Purchase>> getByClient(String cliendId) {
       return compraCrudRepository.findByIdCliente(cliendId)
               .map(compras -> purchaseMapper.toPurchases(compras));
    }

    @Override
    public Purchase save(Purchase purchase) {
        Compra compra= purchaseMapper.toCompra(purchase);
        compra.getProductos().forEach(producto -> producto.setCompra(compra));
        return purchaseMapper.toPurchase(compraCrudRepository.save(compra));
    }
    @Override
    public void delete(int purchaseId){
        compraCrudRepository.deleteById(purchaseId);
    }
}
