package com.cursosdedesarrollo.unoauno;

/**
 * OneToOne BIDIRECCIONAL — lado dueño.
 *
 * La FK perfil_id vive en la tabla usuarios.
 * cascade=all: guardar Usuario guarda Perfil; borrar Usuario borra Perfil.
 *
 * Para mantener la coherencia bidireccional en memoria hay que asignar
 * los dos lados: usuario.setPerfil(p) Y perfil.setUsuario(usuario).
 * El método setPerfil() se encarga internamente de esto.
 */
public class Usuario {

    private Long id;
    private String login;
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
        if (p != null) p.setUsuario(this);
    }
}
