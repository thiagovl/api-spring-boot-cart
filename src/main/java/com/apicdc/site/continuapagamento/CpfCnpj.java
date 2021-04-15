package com.apicdc.site.continuapagamento;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/* Expostado da annotation @CPF */
@Documented
@Constraint(validatedBy = { CpfCnpjValidator.class }) // Classe que irá fazer a validação 
@Target({ FIELD }) // Aplicado somente a um atributo
@Retention(RUNTIME) // Pode ser lida em tempo de execução
public @interface CpfCnpj {
	
	/* Expostado da annotation @CPF 
	 * Alterar o nome do pacote por convensão, inserir o nome do projeto com.apicdc.CpfCnpj.message
	 */
	String message() default "{com.apicdc.CpfCnpj.message}"; // Mensagem default para a annotation
	Class<?>[] groups() default { }; // Annotation pode ser aplicada por cada grupo diferente
	Class<? extends Payload>[] payload() default { };
	
}
