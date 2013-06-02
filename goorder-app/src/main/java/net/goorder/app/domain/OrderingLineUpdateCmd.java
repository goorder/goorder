package net.goorder.app.domain;

import net.goorder.app.infrastructure.Update;
import lombok.Data;

/**
 *
 * @author witoldsz
 */
@Data
public class OrderingLineUpdateCmd {

    private String groupId;

    private Long tableId;

    private Long lineId;

    private Update<String> who, what;

    private Update<Integer> price, paid;

}
