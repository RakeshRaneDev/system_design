package org.byte_stroke.inmemory_database;

import java.util.function.Predicate;
import java.util.Objects;

/**
 * Represents a query condition for filtering database entries
 */
public class QueryCondition {
    private final String field;
    private final Object value;
    private final ComparisonOperator operator;
    private final Predicate<DatabaseEntry> predicate;

    public QueryCondition(String field, Object value, ComparisonOperator operator) {
        this.field = field;
        this.value = value;
        this.operator = operator;
        this.predicate = createPredicate();
    }

    public QueryCondition(Predicate<DatabaseEntry> predicate) {
        this.field = null;
        this.value = null;
        this.operator = null;
        this.predicate = predicate;
    }

    public String getField() {
        return field;
    }

    public Object getValue() {
        return value;
    }

    public ComparisonOperator getOperator() {
        return operator;
    }

    public Predicate<DatabaseEntry> getPredicate() {
        return predicate;
    }

    private Predicate<DatabaseEntry> createPredicate() {
        return entry -> {
            Object fieldValue = getFieldValue(entry, field);
            if (fieldValue == null) return false;

            switch (operator) {
                case EQUALS:
                    return Objects.equals(fieldValue, value);
                case NOT_EQUALS:
                    return !Objects.equals(fieldValue, value);
                case GREATER_THAN:
                    return compareValues(fieldValue, value) > 0;
                case GREATER_THAN_OR_EQUALS:
                    return compareValues(fieldValue, value) >= 0;
                case LESS_THAN:
                    return compareValues(fieldValue, value) < 0;
                case LESS_THAN_OR_EQUALS:
                    return compareValues(fieldValue, value) <= 0;
                case CONTAINS:
                    return fieldValue.toString().toLowerCase().contains(value.toString().toLowerCase());
                case STARTS_WITH:
                    return fieldValue.toString().toLowerCase().startsWith(value.toString().toLowerCase());
                case ENDS_WITH:
                    return fieldValue.toString().toLowerCase().endsWith(value.toString().toLowerCase());
                case REGEX:
                    return fieldValue.toString().matches(value.toString());
                default:
                    return false;
            }
        };
    }

    private Object getFieldValue(DatabaseEntry entry, String field) {
        if ("key".equals(field)) {
            return entry.getKey();
        } else if ("value".equals(field)) {
            return entry.getValue();
        } else if ("createdAt".equals(field)) {
            return entry.getCreatedAt();
        } else if ("expiresAt".equals(field)) {
            return entry.getExpiresAt();
        } else if ("lastAccessedAt".equals(field)) {
            return entry.getLastAccessedAt();
        } else if (entry.getMetadata() != null && entry.getMetadata().containsKey(field)) {
            return entry.getMetadata().get(field);
        }
        return null;
    }

    private int compareValues(Object fieldValue, Object value) {
        if (fieldValue instanceof Comparable && value instanceof Comparable) {
            try {
                return ((Comparable) fieldValue).compareTo(value);
            } catch (ClassCastException e) {
                return fieldValue.toString().compareTo(value.toString());
            }
        }
        return fieldValue.toString().compareTo(value.toString());
    }

    public enum ComparisonOperator {
        EQUALS,
        NOT_EQUALS,
        GREATER_THAN,
        GREATER_THAN_OR_EQUALS,
        LESS_THAN,
        LESS_THAN_OR_EQUALS,
        CONTAINS,
        STARTS_WITH,
        ENDS_WITH,
        REGEX
    }
}