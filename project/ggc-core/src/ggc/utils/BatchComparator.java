package ggc.utils;

import java.util.Comparator;

import ggc.products.Batch;

public class BatchComparator implements Comparator<Batch> {

    @Override
    public int compare(Batch o1, Batch o2) {

        Double price1 = o1.getPrice(), price2 = o2.getPrice();
        Double stock1 = o1.getStock(), stock2 = o1.getStock();

        int productCompare = o1.getProductID().compareTo(o2.getProductID());
        int partnerCompare = o2.getPartnerID().compareTo(o2.getPartnerID());
        int priceCompare = price1.compareTo(price2);
        int stockCompare = stock1.compareTo(stock2);

        if(productCompare == 0 & partnerCompare == 0 & priceCompare == 0){
            return stockCompare;
        }

        if(productCompare == 0 & partnerCompare == 0){
            return priceCompare;
        }

        return (productCompare == 0) ? partnerCompare : productCompare;
    }
    
}
