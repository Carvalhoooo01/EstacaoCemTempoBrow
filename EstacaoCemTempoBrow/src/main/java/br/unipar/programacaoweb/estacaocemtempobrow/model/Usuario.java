package br.unipar.programacaoweb.estacaocemtempobrow.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
public class Usuario implements Serializable, UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String username;

    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roleList = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {

        return this.roleList.stream().map(

                role -> new SimpleGrantedAuthority
                (

                    role.getPermissao().toString()

                )

        ).collect(Collectors.toList());

    }

    @Override
    public String getPassword() {
        return "";
    }

}
