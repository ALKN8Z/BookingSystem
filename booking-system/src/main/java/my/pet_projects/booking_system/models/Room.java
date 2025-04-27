package my.pet_projects.booking_system.models;


import jakarta.persistence.*;
import lombok.Data;

@Table(name = "room")
@Entity
@Data
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "capacity", nullable = false)
    private Long capacity;
}
