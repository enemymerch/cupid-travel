package com.mcan.cupidtravel.utils;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class JpaSpecificationUtils {

    public static <T> Specification<T> buildFindByEqualsSpecification(Class<T> tClass, String fieldName, Object value) {
        return (root, query, criteriaBuilder) -> {
            if (value == null) {
                return criteriaBuilder.conjunction();
            } else {
                return criteriaBuilder.equal(root.get(fieldName), value);
            }
        };
    }

    public static <T, K> Specification<T> buildFindByJoinLikeSpecification(Class<T> t1Class, Class<K> joinClass, String joinedField, String fieldName, String value) {
        return (root, query, criteriaBuilder) -> {
            if (value == null) {
                return criteriaBuilder.conjunction();
            } else {
                Join<T, K> join = root.join(joinedField, JoinType.LEFT);
                return criteriaBuilder.like(criteriaBuilder.lower(join.get(fieldName)), "%" + value.toLowerCase() + "%");
            }
        };
    }

    public static <T> Specification<T> buildFindByEqualsOrGreaterSpecification(String fieldName, Double value) {
        return (root, query, criteriaBuilder) -> {
            if (value == null) {
                return criteriaBuilder.conjunction();
            } else {
                return criteriaBuilder.greaterThanOrEqualTo(root.get(fieldName), value);
            }
        };
    }
}
