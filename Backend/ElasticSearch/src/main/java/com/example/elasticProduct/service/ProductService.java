package com.example.elasticProduct.service;

import com.example.elasticProduct.documents.Products;
import com.example.elasticProduct.helper.Indices;
import com.example.elasticProduct.search.SearchRequestDTO;
import com.example.elasticProduct.search.util.SearchUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class ProductService {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final Logger LOG = LoggerFactory.getLogger(ProductService.class);
    private final RestHighLevelClient client;

    @Autowired
    public ProductService(RestHighLevelClient client) {
        this.client = client;
    }

    public Boolean index(final Products products) {
        try {
            final String productsAsString = MAPPER.writeValueAsString(products);

            final IndexRequest request = new IndexRequest(Indices.PRODUCTS_INDEX);
            request.id(products.getProductName());
            request.source(productsAsString, XContentType.JSON);

            final IndexResponse response = client.index(request, RequestOptions.DEFAULT);
            return response != null && response.status().equals(RestStatus.OK);
        }catch (final Exception e) {
            LOG.error(e.getMessage(), e);
            return false;
        }
    }

    public Products getById(final String productName) {
        try {
            final GetResponse documentFields = client.get(
                    new GetRequest(Indices.PRODUCTS_INDEX, productName),
                    RequestOptions.DEFAULT
            );
            if(documentFields == null || documentFields.isSourceEmpty()) {
                return null;
            }
            return MAPPER.readValue(documentFields.getSourceAsString(), Products.class);
        }catch (final Exception e) {
            LOG.error(e.getMessage(), e);
            return null;
        }
    }



    public List<Products> search(final SearchRequestDTO dto) {
        final SearchRequest request = SearchUtil.buildSearchRequest(
                Indices.PRODUCTS_INDEX,
                dto
        );

        return searchInternal(request);
    }

    private List<Products> searchInternal(final SearchRequest request) {
        if (request == null) {
            LOG.error("Failed to build search request");
            return Collections.emptyList();
        }

        try {
            final SearchResponse response = client.search(request, RequestOptions.DEFAULT);

            final SearchHit[] searchHits = response.getHits().getHits();
            final List<Products> products = new ArrayList<>(searchHits.length);
            for (SearchHit hit : searchHits) {
                products.add(
                        MAPPER.readValue(hit.getSourceAsString(), Products.class)
                );
            }
            return products;
        } catch (Exception e) {
            System.out.println("In Exception");
            LOG.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }
}
