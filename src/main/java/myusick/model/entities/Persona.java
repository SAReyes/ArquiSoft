package myusick.model.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "Persona")
public class Persona implements Serializable{

    @Id
//    @EmbeddedId
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "publicante_uuid",insertable=true,updatable=true,nullable=true,unique=true)
	private Publicante publicante_uuid;

	@Column(name = "nombre", nullable = false, length = 20)
	private String nombre;

	@Column(name = "apellidos", nullable = true, length = 60)
	private String apellidos;

	@Column(name = "password", nullable = false, length = 20)
	private String password;
	
	@Column(name = "email", nullable = false, length = 60)
	private String email;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "fechanacimiento", nullable = false)
	private Date fechanacimiento;

	@Column(name = "ciudad", nullable = false, length = 45)
	private String ciudad;
	
	@Column(name = "pais", nullable = false, length = 45)
	private String pais;
	
	@Column(name = "telefono", nullable = true)
	private String telefono;
	
	/*------RELACIONES------*/
	
	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(name="tiene_aptitud", 
			joinColumns={@JoinColumn(name="uuid_p", referencedColumnName="publicante_uuid")}, 
			inverseJoinColumns={@JoinColumn(name="idaptitud", referencedColumnName="idAptitud")})
	private Set<Aptitud> aptitudes = new HashSet<Aptitud>();
	
	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(name="persona_tiene_tag", 
			joinColumns={@JoinColumn(name="uuid_p", referencedColumnName="publicante_uuid")}, 
			inverseJoinColumns={@JoinColumn(name="idtag", referencedColumnName="idtag")})
	private Set<Tag> tags_persona = new HashSet<Tag>();
	
	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(name="es_integrante", 
			joinColumns={@JoinColumn(name="uuid_p", referencedColumnName="publicante_uuid")}, 
			inverseJoinColumns={@JoinColumn(name="uuid_g", referencedColumnName="publicante_uuid")})
	private Set<Grupo> grupos = new HashSet<Grupo>();

	
	/*------GETTERS/SETTERS------*/

	public Publicante getPublicante_uuid() {
		return publicante_uuid;
	}

	public void setPublicante_uuid(Publicante publicante_uuid) {
		this.publicante_uuid = publicante_uuid;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String apnombre) {
		this.nombre = apnombre;
	}
	
	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getFechanacimiento() {
		return fechanacimiento;
	}

	public void setFechanacimiento(Date fechanacimiento) {
		this.fechanacimiento = fechanacimiento;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

    public boolean addGrupo(Grupo g){
        boolean exito = false;
        try{
            grupos.add(g); exito=true;
        }catch(Exception ex){/*nada, exito permanece false*/ }

        return exito;
    }

    public boolean addTag(Tag t){
        boolean exito = false;
        try{
            tags_persona.add(t); exito=true;
        }catch(Exception ex){/*nada, exito permanece false*/ }

        return exito;
    }

    public boolean addAptitud(Aptitud apt){
        boolean exito = false;
        try{
            aptitudes.add(apt); exito=true;
        }catch(Exception ex){/*nada, exito permanece false*/ }

        return exito;
    }
}
