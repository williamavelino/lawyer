package br.com.lawyer.core.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * Descreve o contrato de uma entidade que possui uma chave (IUID)
 *
 * @author Deividi Cavarzan
 */
@NoRepositoryBean
public interface JPABaseRepository<B extends Serializable, T extends IUID<B>> extends JpaRepository<T, B>, QueryDslPredicateExecutor<T>, JpaSpecificationExecutor<T> {

}