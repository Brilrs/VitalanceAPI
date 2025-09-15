    package org.example.vitalance.repositorios;

    import org.example.vitalance.entidades.Role;
    import org.springframework.data.jpa.repository.JpaRepository;

    public interface RoleRepository extends JpaRepository<Role, Long> {
    }
