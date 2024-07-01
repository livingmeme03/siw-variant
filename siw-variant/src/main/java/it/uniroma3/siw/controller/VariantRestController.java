package it.uniroma3.siw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import it.uniroma3.siw.model.Variant;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.siw.service.VariantService;

@RestController
public class VariantRestController {
	
	@Autowired
	private VariantService variantService;
	
	@GetMapping("/rest/variant/{id}")
	public Variant getVariant(@PathVariable("id") Long id) {
		return this.variantService.findById(id);
	}
	
	@GetMapping("/rest/elencoVariant")
	public List<Variant> getElencoVariant() {
		return (List<Variant>)this.variantService.findAll();
	}
}

