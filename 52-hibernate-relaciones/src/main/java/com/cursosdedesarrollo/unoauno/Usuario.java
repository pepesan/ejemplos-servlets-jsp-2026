package com.cursosdedesarrollo.unoauno;

import javax.persistence.*;

/**
 * OneToOne BIDIRECCIONAL — lado dueño.
 *
 * La FK perfil_id vive en la tabla usuarios.
 * cascade=ALL: guardar Usuario guarda Perfil; borrar Usuario borra Perfil.
 *
 * Para mantener la coherencia bidireccional en memoria hay que asignar
 * los dos lados: usuario.setPerfil(p) Y perfil.setUsuario(usuario).
 */
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 60)
    private String login;

    /** Lado dueño: genera la columna perfil_id en la tabla usuarios. */
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "perfil_id")
    private Perfil perfil;

    public Usuario() {}
    public Usuario(String login) { this.login = login; }

    public Long   getId()               { return id; }
    public void   setId(Long id)        { this.id = id; }
    public String getLogin()            { return login; }
    public void   setLogin(String l)    { this.login = l; }
    public Perfil getPerfil()           { return perfil; }
    public void   setPerfil(Perfil p)   {
        this.perfil = p;
        if (p != null) p.setUsuario(this);  // mantiene coherencia bidireccional
    }
}
