/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.edu.cibertec.view.managedbeans;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import pe.edu.cibertec.dominio.Carrito;
import pe.edu.cibertec.dominio.DetalleCarrito;
import pe.edu.cibertec.dominio.Producto;
import pe.edu.cibertec.dominio.Usuario;
import pe.edu.cibertec.repositorio.CarritoRepositorio;
import pe.edu.cibertec.repositorio.DetalleCarritoRepositorio;
import pe.edu.cibertec.repositorio.impl.MybatisCarritoRepositorioImpl;
import pe.edu.cibertec.repositorio.impl.MybatisDetalleCarritoRepositorioImpl;

/**
 *
 * @author Poderosoans
 */

@ManagedBean
@SessionScoped
public class CarritoBean {
    private DetalleCarrito detalleCarrito;
    private List<DetalleCarrito> listDetalleCarrito = new ArrayList<DetalleCarrito>();
    private Carrito carrito = new Carrito();
    private int cantidad = 1;
    private BigDecimal total;

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public List<DetalleCarrito> getListDetalleCarrito() {
        return listDetalleCarrito;
    }

    public void setListDetalleCarrito(List<DetalleCarrito> listDetalleCarrito) {
        this.listDetalleCarrito = listDetalleCarrito;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public DetalleCarrito getDetalleCarrito() {
        return detalleCarrito;
    }

    public void setDetalleCarrito(DetalleCarrito detalleCarrito) {
        this.detalleCarrito = detalleCarrito;
    }
   
    public String addCarrito(Producto producto) {
        
        if(producto != null) {
            System.out.println(producto.getDescripcion());
        }
        // verifica que tiene carrito disponible
        
        // si no tiene crea uno
        
        // Crear carrito
        
        SqlSessionFactory ssf = (SqlSessionFactory)FacesContext.getCurrentInstance().getExternalContext()
                .getApplicationMap().get("ssf");
        
        // Session de usuario
        Usuario usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        Carrito carrito = new Carrito();
        carrito.setActivo(1);
        carrito.setTotal(BigDecimal.ZERO);
        carrito.setUsuario(usuario);
       
        try (SqlSession session = ssf.openSession()) {
            CarritoRepositorio carritoRepositorio = new MybatisCarritoRepositorioImpl(session);
            carritoRepositorio.crear(carrito);
        }
        

        // Crear detalle carrito
        detalleCarrito = new DetalleCarrito();
        detalleCarrito.setCantidad(cantidad);
        detalleCarrito.setCarritoCompras(carrito);
        detalleCarrito.setProducto(producto);
        detalleCarrito.setPrecioUnitario(producto.getPrecio());
        listDetalleCarrito.add(detalleCarrito);
        
        total = BigDecimal.ZERO;
        for( DetalleCarrito l : listDetalleCarrito ){
            System.out.println( l );
            //total = l.getPrecioUnitario(); 
            total = total
                .add(l.getPrecioUnitario());
        }
        
        //total = detalleCarrito.getPrecioUnitario();
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("listDetalleCarrito", listDetalleCarrito);
        return "pasarela.xhtml?faces-redirect=true";  
    }
    
    public String processCarrito() {
        // inserta en detalle carrito
        SqlSessionFactory ssf = (SqlSessionFactory)FacesContext.getCurrentInstance().getExternalContext()
                .getApplicationMap().get("ssf");
        try (SqlSession session = ssf.openSession()) {
            DetalleCarritoRepositorio detalleCarritoRepositorio = new MybatisDetalleCarritoRepositorioImpl(session);
            // consulta
        }
        
        //for y setter
        
        // elimina la session del listDetalleCarrito o lo reinicia
        
        // actualiza carrito a 0 y el total
        return "productos.xhtml?faces-redirect=true";
    }
}
