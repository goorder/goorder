package net.goorder.app.domain;

import net.goorder.app.infrastructure.Update;
import lombok.Data;

/**
 *
 * @author witoldsz
 */
@Data
public class OrderingTableUpdateCmd {

    private String groupId;

    private Long tableId;

    private Update<String> label, place, comments;

}
