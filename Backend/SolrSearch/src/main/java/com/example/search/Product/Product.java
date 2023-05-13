package com.example.search.Product;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;
import org.springframework.stereotype.Component;

@Data
@JsonIgnoreProperties
@AllArgsConstructor
@NoArgsConstructor
@Component
@SolrDocument(solrCoreName = "solr_search")

public class Product {
    @Id
    @Indexed(name = "productId", type = "string")
    private String productId;
    @Indexed(name = "productName", type = "string")
    private String productName;
    @Indexed(name = "productColor", type = "string")
    private String productColor;
    @Indexed(name = "productRAM", type = "string")
    private String productRAM;
    @Indexed(name = "productStorage", type = "string")
    private String productStorage;
    @Indexed(name = "productBattery", type = "string")
    private String productBattery;
    @Indexed(name = "productProcessor", type = "string")
    private String productProcessor;
    @Indexed(name = "productImg", type = "string")
    private String productImg;

}
