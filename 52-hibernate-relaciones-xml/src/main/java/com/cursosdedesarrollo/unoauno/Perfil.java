package com.cursosdedesarrollo.unoauno;

/**
 * OneToOne BIDIRECCIONAL — lado inverso.
 *
 * mappedBy en HBM: one-to-one property-ref="perfil" en Perfil.hbm.xml.
 * Perfil no genera FK; la FK perfil_id está en la tabla usuarios.
 */
public class Perfil {

    private Long id;
    private String bio;
    private String linkedin;
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
