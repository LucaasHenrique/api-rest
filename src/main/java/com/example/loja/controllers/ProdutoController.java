package com.example.loja.controllers;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import com.example.loja.models.DTO.ProdutoRecordDTO;
import com.example.loja.models.Produtos;
import com.example.loja.repositories.ProdutoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

@RestController
public class ProdutoController {

    @Autowired
    ProdutoRepository pr;

    @GetMapping("/produtos")
    public ResponseEntity<List<Produtos>> getAllProdutos() {
        List<Produtos> produtosList = pr.findAll();
        if (!produtosList.isEmpty()) {
            for (Produtos produto: produtosList) {
                long id = produto.getIdProduto();
                produto.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProdutoController.class).getOneProduto(id)).withSelfRel());
            }
        }
            return new ResponseEntity<List<Produtos>>(produtosList, HttpStatus.OK);
    }


    @GetMapping("/produtos/{id}")
    public ResponseEntity<Object> getOneProduto(@PathVariable(value = "id") long id) {
        Optional<Produtos> produtoO = pr.findById(id);
        if (produtoO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("product not found");
        }
        produtoO.get().add(linkTo(methodOn(ProdutoController.class).getAllProdutos()).withRel("Products list"));
        return ResponseEntity.status(HttpStatus.OK).body(produtoO.get());
    }

    @PostMapping("/produtos")
    public ResponseEntity<Produtos> saveProduto(@RequestBody @Valid ProdutoRecordDTO produto) {
        var produto1 = new Produtos();
        BeanUtils.copyProperties(produto, produto1);
        return ResponseEntity.status(HttpStatus.CREATED).body(pr.save(produto1));
    }

    @DeleteMapping("/produtos/{id}")
    public ResponseEntity<Object> deleteProduto(@PathVariable(value = "id") long id) {
        Optional<Produtos> produtoO = pr.findById(id);
        if (!produtoO.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        pr.delete(produtoO.get());
        return ResponseEntity.status(HttpStatus.OK).body("Product deleted!");
    }

    @PutMapping("/produtos/{id}")
    public ResponseEntity<Produtos> updateProduto(@PathVariable(value = "id") long id,
                                                      @RequestBody @Valid ProdutoRecordDTO produtoDTO) {
        Optional<Produtos> produtoO = pr.findById(id);
        if (!produtoO.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        var produtoModel = produtoO.get();
        BeanUtils.copyProperties(produtoDTO, produtoModel);
        return new ResponseEntity<Produtos>(pr.save(produtoModel), HttpStatus.OK);
    }
}
