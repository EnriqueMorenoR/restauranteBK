/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ues.occ.edu.sv.restaurantebk.tpi.entities;

import java.io.Serializable;
import java.util.List;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author enrique
 */
@Entity
@Table(name = "producto", catalog = "calendario", schema = "")
@NamedQueries({
    @NamedQuery(name = "Producto.findAll", query = "SELECT p FROM Producto p"),
    @NamedQuery(name = "Producto.findByIdProducto", query = "SELECT p FROM Producto p WHERE p.idProducto = :idProducto"),
    @NamedQuery(name = "Producto.findByNombreProducto", query = "SELECT p FROM Producto p WHERE p.nombreProducto = :nombreProducto"),
    @NamedQuery(name = "Producto.findByPrecio", query = "SELECT p FROM Producto p WHERE p.precio = :precio"),
    @NamedQuery(name = "Producto.findByEsPreparado", query = "SELECT p FROM Producto p WHERE p.esPreparado = :esPreparado")})
public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_producto", nullable = false)
    private Integer idProducto;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nombre_producto", nullable = false, length = 100)
    private String nombreProducto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "precio", nullable = false)
    private double precio;
    @Column(name = "es_preparado")
    private Boolean esPreparado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "producto")
    private List<DetalleOrden> detalleOrdenList;
    @JoinColumn(name = "id_categoria", referencedColumnName = "id_categoria", nullable = false)
    @ManyToOne(optional = false)
    private Categoria idCategoria;

    public Producto() {
    }

    public Producto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public Producto(Integer idProducto,Categoria idCategoria, String nombreProducto, double precio, boolean esPreparado) {
        this.idProducto = idProducto;
        this.idCategoria = idCategoria;
        this.nombreProducto = nombreProducto;
        this.precio = precio;
        this.esPreparado = esPreparado;
    }

    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Boolean getEsPreparado() {
        return esPreparado;
    }

    public void setEsPreparado(Boolean esPreparado) {
        this.esPreparado = esPreparado;
    }

    @JsonbTransient
    public List<DetalleOrden> getDetalleOrdenList() {
        return detalleOrdenList;
    }

    public void setDetalleOrdenList(List<DetalleOrden> detalleOrdenList) {
        this.detalleOrdenList = detalleOrdenList;
    }

    public Categoria getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Categoria idCategoria) {
        this.idCategoria = idCategoria;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProducto != null ? idProducto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Producto)) {
            return false;
        }
        Producto other = (Producto) object;
        return !((this.idProducto == null && other.idProducto != null) || (this.idProducto != null && !this.idProducto.equals(other.idProducto)));
    }

    @Override
    public String toString() {
        return "ues.occ.edu.sv.calendarbk.tpi.entities.Producto[ idProducto=" + idProducto + " ]";
    }

}
