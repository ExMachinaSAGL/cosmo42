package ch.exmachina.cosmo42.services.kb.schema;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ChunkType {
    TEXT,
    TABLE,
    IMAGE;
	
    private final String label;

    private ChunkType(){
    	label = name().toLowerCase();    	
    }
    
    private ChunkType(String label){
        this.label = label;
    }
	    
    // @JsonValue tells Jackson to use this method's return value for SERIALIZATION
    @JsonValue
    public String getCode() { 
        return label; 
    }

    // @JsonCreator tells Jackson to use this factory method for DESERIALIZATION
    @JsonCreator
    public static ChunkType fromLabel(String label) {
        for (var type : ChunkType.values()) {
            if (type.label.equals(label)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid chunk type: " + label);
    }
}
