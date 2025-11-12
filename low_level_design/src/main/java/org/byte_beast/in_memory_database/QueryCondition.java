package org.byte_beast.in_memory_database;

import java.util.Objects;
import java.util.function.Predicate;

public class QueryCondition {
    private String field;
    private Object value;
    private ComparisonOperator operator;
    private Predicate<DataBaseEntry> predicate;

    public QueryCondition(String field, Object value, ComparisonOperator operator) {
        this.field = field;
        this.value = value;
        this.operator = operator;
        this.predicate = createPredicate();
    }

    public QueryCondition( Predicate<DataBaseEntry> predicate) {
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

    public Predicate<DataBaseEntry> getPredicate() {
        return predicate;
    }

    private Predicate<DataBaseEntry> createPredicate(){
        return entry -> {
            Object fieldValue = getFieldValue(entry, field);
            if(fieldValue==null) return false;
            switch (operator){
               case EQUALS:
                  return Objects.equals(value, fieldValue);
                case NOT_EQUALS:
                    return !Objects.equals(value, fieldValue);
                case GREATER_THAN:
                    return compareValues(fieldValue, value)>0;
                case GREATER_THAN_OR_EQUALS:
                    return compareValues(fieldValue, value)>=0;
                case LESS_THAN:
                    return compareValues(fieldValue, value)<0;
                case LESS_THAN_OR_EQUALS:
                    return compareValues(fieldValue, value)<=0;
                case CONTAINS:
                    return fieldValue.toString().toLowerCase().contains(value.toString().toLowerCase());
                case START_WITH:
                   return fieldValue.toString().toLowerCase().startsWith(value.toString().toLowerCase());
                case END_WITH:
                    return fieldValue.toString().toLowerCase().endsWith(value.toString().toLowerCase());
                case REGEX:
                    fieldValue.toString().matches(value.toString());
                default:
                    return false;
            }
        };
    }

    private int compareValues(Object fieldValue, Object value){
        if( fieldValue instanceof Comparable && value instanceof Comparable){
            try{
                return ((Comparable) fieldValue).compareTo(value);

            }catch (ClassCastException e){
                 return fieldValue.toString().compareTo(value.toString());
            }
        }else{
            return  fieldValue.toString().compareTo(value.toString());
        }
    }


    private Object getFieldValue(DataBaseEntry entry,  String field){
        if("key".equals(field)){
            return entry.getKey();
        }
        if("value".equals(field)){
            return entry.getValue();
        }
        if("createdAt".equals(field)){
            return entry.getCreatedAt();
        }
        if("expireAt".equals(field)){
            return entry.getExpireAt();
        }
        if("lastAccessedAt".equals(field)){
            return entry.getLastAccessedAt();
        }
        if(entry.getMataData()!=null && entry.getMataData().containsKey(field)){
           return  entry.getMataData().get(field);
        }
        return null;
    }
}
