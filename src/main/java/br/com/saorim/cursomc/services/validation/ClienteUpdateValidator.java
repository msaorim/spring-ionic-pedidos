package br.com.saorim.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import br.com.saorim.cursomc.controllers.exceptions.FieldMessage;
import br.com.saorim.cursomc.dto.ClienteDTO;
import br.com.saorim.cursomc.entities.Cliente;
import br.com.saorim.cursomc.repositories.ClienteRepository;

public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> {
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Override
	public void initialize(ClienteUpdate ann) {
		// empty
	}

	@Override
	public boolean isValid(ClienteDTO objDto, ConstraintValidatorContext context) {
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Map<String, String> map = (Map)request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Integer uriId = (Integer.parseInt(map.get("id")));
		List<FieldMessage> list = new ArrayList<>();

		Cliente objAux = clienteRepository.findByEmail(objDto.getEmail());
		
		if(objAux != null && !objAux.getId().equals(uriId)) {
			list.add(new FieldMessage("email", "Email já cadastrado em outro Cliente!!!"));
		}
		
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}