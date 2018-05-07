/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.edu.cibertec.view.managedbeans;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import pe.edu.cibertec.dominio.Categoria;
import pe.edu.cibertec.dominio.Producto;
import pe.edu.cibertec.repositorio.CategoriaRepositorio;
import pe.edu.cibertec.repositorio.ProductoRepositorio;
import pe.edu.cibertec.repositorio.impl.MybatisCategoriaRepositorioImpl;
import pe.edu.cibertec.repositorio.impl.MybatisProductoRepositorioImpl;

@ManagedBean
@ViewScoped
public class ProductosBean {
    private List<Producto> lstProducto;
    private Producto producto;
    private List<Categoria> lstCategoria;
    
    public ProductosBean(){
    }
    
    @PostConstruct
    public void init() {
        SqlSessionFactory ssf = (SqlSessionFactory)FacesContext.getCurrentInstance().getExternalContext()
                .getApplicationMap().get("ssf");
        try (SqlSession session = ssf.openSession()) {
            ProductoRepositorio productoRepositorio = new MybatisProductoRepositorioImpl(session);
            lstProducto = productoRepositorio.obtenerTodos();
            
            CategoriaRepositorio categoriaRepositorio = new MybatisCategoriaRepositorioImpl(session);
            lstCategoria = categoriaRepositorio.obtenerTodos();
        }
        
        // no tiene argumentos, es void, public, no debe lanzar excepción, anotación @PostConstruct  
        producto = new Producto();
    }

    public List<Producto> getLstProducto() {
        return lstProducto;
    }

    public void setLstProducto(List<Producto> lstProducto) {
        this.lstProducto = lstProducto;
    }

    public List<Categoria> getLstCategoria() {
        return lstCategoria;
    }

    public void setLstCategoria(List<Categoria> lstCategoria) {
        this.lstCategoria = lstCategoria;
    }
    
    
}
