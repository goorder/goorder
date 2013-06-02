package net.goorder.app.infrastructure;

/**
 *
 * @author Witold Szczerba <witold.szczerba@espeo.pl>
 */
public interface Sequencer {

    /**
     * @return next value from default sequence.
     */
    Long next();
    
    /**
     * @return next value from sequence.
     */
    Long next(String sequenceName);
    
}
