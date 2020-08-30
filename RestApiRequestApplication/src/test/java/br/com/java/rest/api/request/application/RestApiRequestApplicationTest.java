package br.com.java.rest.api.request.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 *
 * @author lucas
 */
public class RestApiRequestApplicationTest {
   
    @Test
    public void caseA() {
        assertEquals(58, RestApiRequestApplication.calculateTotalGoals(2014, "Tottenham Hotspur"));
    }
    
    @Test
    public void caseB() {
        assertEquals(35, RestApiRequestApplication.calculateTotalGoals(2011, "Barcelona"));
    }
    
    @Test
    public void caseC() {
        assertEquals(74, RestApiRequestApplication.calculateTotalGoals(2015, "Chelsea"));
    }
    
    @Test
    public void caseD() {
        assertEquals(129, RestApiRequestApplication.calculateTotalGoals(2012, "Real Madrid"));
    }
    
    @Test
    public void caseE() {
        assertEquals(77, RestApiRequestApplication.calculateTotalGoals(2016, "Juventus"));
    }
}
