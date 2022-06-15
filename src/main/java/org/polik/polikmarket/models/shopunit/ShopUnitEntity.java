package org.polik.polikmarket.models.shopunit;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Created by Polik on 6/10/2022
 */
@Entity
@Table(name = "shop_unit")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = {"id", "name", "date", "type"})
@EqualsAndHashCode(of = "id")
//@SqlResultSetMapping(
//        name = "ShopUnit.getShopUnit",
//        classes = {
//                @ConstructorResult(
//                        targetClass = ShopUnit.class,
//                        columns = {
//                                @ColumnResult(name = "id", type = UUID.class),
//                                @ColumnResult(name = "name", type = String.class),
//                                @ColumnResult(name = "parent_id", type = UUID.class),
//                                @ColumnResult(name = "date", type = LocalDateTime.class),
//                                @ColumnResult(name = "type", type = String.class),
//                                @ColumnResult(name = "price", type = BigDecimal.class)
//                        }
//                )
//        }
//)
//@NamedNativeQuery(
//        name = "ShopUnit.getShopUnit",
//        query = SQLUtil.RECURSIVE_SEARCH_QUERY,
//        resultClass = ShopUnit.class,
//        resultSetMapping = "ShopUnit.getShopUnit"
//)
public class ShopUnitEntity {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @ManyToOne(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ShopUnitEntity parent;

    @Column(name = "type", updatable = false, nullable = false)
    @Enumerated(EnumType.STRING)
    private ShopUnitType type;

    @Column(name = "price")
    @Min(value = 0)
    private Long price;
}
