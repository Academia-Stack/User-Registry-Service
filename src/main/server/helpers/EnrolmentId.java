package helpers;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.util.UUID;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class EnrolmentId implements java.io.Serializable {
    @Column(nullable = false, length = 30)
    private UUID studentIdentifier;

    @Column(nullable = false, length = 30)
    private UUID courseIdentifier;
}