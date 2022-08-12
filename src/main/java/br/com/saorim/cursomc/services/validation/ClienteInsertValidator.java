package br.com.saorim.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.saorim.cursomc.controllers.exceptions.FieldMessage;
import br.com.saorim.cursomc.dto.ClienteNewDTO;
import br.com.saorim.cursomc.entities.Cliente;
import br.com.saorim.cursomc.entities.enums.TipoCliente;
import br.com.saorim.cursomc.repositories.ClienteRepository;
import br.com.saorim.cursomc.services.validation.utils.BR;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Override
	public void initialize(ClienteInsert ann) {
	}

	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();

		// inclua os testes aqui, inserindo erros na lista
		
		if(objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) &&
				!BR.isValidCpf(objDto.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj", "CPF inválido!!!"));
		}
		
		if(objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod()) &&
				!BR.isValidCnpj(objDto.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido!!!"));
		}
		
		Cliente objAux = clienteRepository.findByEmail(objDto.getEmail());
		if(objAux != null) {
			list.add(new FieldMessage("email", "Email já cadastrado!!!"));
		}

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}