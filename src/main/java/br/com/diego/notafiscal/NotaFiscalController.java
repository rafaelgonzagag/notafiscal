package br.com.diego.notafiscal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class NotaFiscalController 
{
	@Autowired
	private NotaFiscalRepository rp;
	private static final String LISTANOTAFISCAL = "listanotasfiscais";
	private static final String NOTASFISCAIS = "notasfiscais";
	
	@RequestMapping("/")
	public String index() 
	{
		return "index";
	}

	@RequestMapping("listanotasfiscais")
	public String listaNotasFiscais(Model model) 
	{
		
		Iterable<NotaFiscal> nf = rp.findAll();
		model.addAttribute(NOTASFISCAIS, nf);
		return LISTANOTAFISCAL;
	}

	@RequestMapping(value = "salvar", method = RequestMethod.POST)
	public String salvar(@RequestParam("nome") String nome, @RequestParam("valor") Double valor, @RequestParam("imposto") String imposto, Model model) 
	{
		Imposto impostoSelecionado;
		
		if (imposto.toUpperCase().trim().compareTo("ICMS") == 0)
		{
			impostoSelecionado = new Icms();
		}
		else
		{
			impostoSelecionado = new Iss();
		}
		
		NotaFiscal nf = new NotaFiscal(nome, impostoSelecionado.valorImposto().doubleValue(), valor);
		rp.save(nf);
		Iterable<NotaFiscal> notaFiscalLista = rp.findAll();
		model.addAttribute(NOTASFISCAIS, notaFiscalLista);
		return LISTANOTAFISCAL;
	}
	
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public String salvar(@RequestParam("id") Integer id, Model model) 
	{
		NotaFiscal notaFiscal = rp.findOne(id.longValue());	
		
		if (notaFiscal != null)
		{
			rp.delete(notaFiscal);
		}
		
		Iterable<NotaFiscal> notaFiscalLista = rp.findAll();
		model.addAttribute(NOTASFISCAIS, notaFiscalLista);
		
		return LISTANOTAFISCAL;
	}
}