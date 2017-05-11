<%@page import="edu.poli.gerencia.votaciones.negocio.utiles.SesionUtil"%>
<%@page import="edu.poli.gerencia.votaciones.modelo.vo.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    if (!SesionUtil.esAdministrador(usuario)) {
        response.sendRedirect("../errors/403.html");
    }
%>
<div class="animated fadeIn">
    <div class="card">
        <div class="card-header">
            <i class="fa fa-edit"></i> Tipos de usuarios
        </div>
        <div class="card-block">            
            <div class="display-block">
                <h2 class="m-b-0"><i class="fa fa-fw fa-plus"></i> Nuevo <a href="#usuarios" class="btn btn-link pull-right" ><i class="fa fa-fw fa-user"></i> Usuarios</a></h2>                
            </div>
            <hr/>
            <form id="formtipousuario">
                <input type="hidden" id="idTipoUsuario" name="idTipoUsuario" />
                <div class="row">
                    <div class="col-md-4">         
                        <div class="form-group">
                            <label for="txtDescripcion">Descripción</label>
                            <input type="text" class="form-control" id="txtDescripcion" name="descripcion" required="">
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="form-group">
                            <label for="cmbPublico">Público</label>
                            <select class="form-control" id="cmbPublico" name="publico" required="">
                                <option value="">Seleccione</option>
                                <option value="true">Si</option>
                                <option value="false">No</option>
                            </select>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <button type="submit" class="btn btn-success m-t-25"><i class="fa fa-fw fa-save"></i> Guardar</button>
                        <button type="reset" class="btn btn-default m-t-25"><i class="fa fa-fw fa-trash"></i> Limpiar</button>
                    </div>
                </div>
            </form>
            <hr/>
            <table id="tablaTiposUsuario" class="table table-striped table-bordered" width="100%"></table>
        </div>
    </div>
</div>
<script src="../assets/js/views/dashboard/admin/tipousuarios.js" type="text/javascript"></script>