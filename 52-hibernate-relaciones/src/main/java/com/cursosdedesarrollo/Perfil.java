package com.cursosdedesarrollo;

import javax.persistence.*;

/**
 * OneToOne BIDIRECCIONAL — lado inverso.
 *
 * mappedBy="perfil" significa: "la FK está en el campo 'perfil' de Usuario,
 * no aquí". Perfil no tiene columna extra; la relación se mantiene por el lado Usuario.
 *
 * Navegación: Perfil → Usuario (lado inverso, sin FK propia)
 */
@Entity
@Table(name = "perfiles")
public class Perfil {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 400)
    private String bio;

    @Column(length = 200)
    private String linkedin;

    /**
     * Lado inverso: no genera FK. mappedBy apunta al campo "perfil" en Usuario.
     * fetch=LAZY: no carga el Usuario al cargar el Perfil.
     */
    @OneToOne(mappedBy = "perfil", fetch = FetchType.LAZY)
    private Usuario usuario;

    public Perfil() {}
    public Perfil(String bio) { this.bio = bio; }
    public Perfil(String bio, String linkedin) { this.bio = bio; this.linkedin = linkedin; }

    public Long    getId()                   { return id; }
    public void    setId(Long id)            { this.id = id; }
    public String  getBio()                  { return bio; }
    public void    setBio(String bio)        { this.bio = bio; }
    public String  getLinkedin()             { return linkedin; }
    public void    setLinkedin(String l)     { this.linkedin = l; }
    public Usuario getUsuario()              { return usuario; }
    public void    setUsuario(Usuario u)     { this.usuario = u; }
}
