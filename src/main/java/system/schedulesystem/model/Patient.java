package system.schedulesystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name="patient")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "name cannot be empty")
    @Column(name = "name", unique = false)
    private String name;

    @NotBlank(message = "Phone cannot be empty")
    @Column(name = "phone", unique = true)
    private String phone;

    @NotBlank(message = "Email cannot be empty")
    @Column(name = "email", unique = true)
    private String email;


    @Column(name = "medical_history", unique = false)
    private String medicalHistory;

    @OneToMany(mappedBy = "patient")
    @JsonIgnore
    private List<Appointment> patientAppointments;

}
