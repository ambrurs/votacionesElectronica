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
            <i class="fa fa-edit"></i> Usuarios registrados
            <div class="card-actions">
                <a href="javascript:;" title="Activar todos los usuarios nuevos"><i class="fa fa-fw fa-check"></i></a>                
            </div>
        </div>
        <div class="card-block">
            <button class="btn btn-primary cursor-pointer" id="btnNuevoUsuario"><i class="fa fa-fw fa-plus"></i> Nuevo</button>
            <a href="#tipousuarios" class="btn btn-link pull-right"><i class="fa fa-fw fa-user"></i> Tipos de usuario</a>
            <hr/>
            <table class="table table-striped table-bordered datatable" id="tablaUsuarios" width="100%">
            </table>
        </div>
    </div>
</div>
<!--MODAL CAMBIAR ESTADO USUARIO-->
<div id="modalCambiarEstado" class="modal fade" role="dialog">
    <div class="modal-dialog modal-sm">
        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h5 class="modal-title">Cambiar estado usuario</h5>
            </div>
            <div class="modal-body">
                <p>Seleccione el nuevo estado para esta cuenta, y haga click en aceptar.</p>
                <div class="estados-radios">
                    <div class="radio">
                        <label class="" for="estado1"><input id="estado1" value="S" type="radio" name="estadoRadio" /> Activo</label>
                    </div>
                    <div class="radio">                        
                        <label class="" for="estado2"><input id="estado2" value="I" type="radio" name="estadoRadio" /> Inactivo</label>
                    </div>
                    <div class="radio">                        
                        <label class="" for="estado3"><input id="estado3" value="B" type="radio" name="estadoRadio" /> Bloqueado</label>                    
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-success" data-dismiss="modal" id="btnAceptar">Aceptar</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
            </div>
        </div>

    </div>
</div>
<!--FIN MODAL CAMBIAR ESTADO USUARIO-->

<!--MODAL DETALLES/EDITAR USUARIO-->
<div id="modalUsuario" class="modal fade" role="dialog">
    <div class="modal-dialog" style="max-width: 600px;">
        <!-- Modal content-->
        <div class="modal-content">
            <form class="" id="formUsuario">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title" id="titulo"></h4>
                </div>
                <div class="modal-body">
                    <div class="card-block p-2">                                                
                        <input type="hidden" name="consUsuario.consUsuario" id="consUsuario" />
                        <input type="hidden" name="consPersona" id="consPersona" />
                        <div class="row">
                            <div class="col-xs-12 col-md-12">
                                <div class="alert alert-success alert-dismissable hidden" id="alertActualizar">
                                    <a href="#" class="custom-close close" >&times;</a>
                                    <span id="text"></span>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <div class="input-group" title="Nombre de usuario">
                                        <span class="input-group-addon"><i class="icon-user"></i>
                                        </span>
                                        <input type="text" class="form-control" placeholder="Nombre de usuario" required="" maxlength="50" id="txtNombreusuario" name="consUsuario.nombreUsuario" />
                                    </div>                                            
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <div class="input-group" title="Correo electrónico">
                                        <span class="input-group-addon">@</span>
                                        <input type="email" class="form-control" placeholder="Correo electrónico" required="" maxlength="150" id="txtCorreoElectronico" name="correo">
                                    </div>                                            
                                </div>  
                            </div>
                        </div>            
                        <div class="row">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <div class="input-group" title="Primer nombre">
                                        <span class="input-group-addon"><i class="icon-user"></i>
                                        </span>
                                        <input type="text" class="form-control" placeholder="Primer nombre" required="" maxlength="50" id="txtPrimerNombre" name="primerNombre" />
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group" title="Segundo nombre">
                                    <div class="input-group">
                                        <span class="input-group-addon"><i class="icon-user"></i>
                                        </span>
                                        <input type="text" class="form-control" placeholder="Segundo nombre" required="" maxlength="50" id="txtSegundoNombre" name="segundoNombre" />
                                    </div>                                            
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <div class="input-group" title="Primer apellido">
                                        <span class="input-group-addon"><i class="icon-user"></i>
                                        </span>
                                        <input type="text" class="form-control" placeholder="Primer Apellido" required="" maxlength="50" id="txtPrimerApellido"  name="primerApellido"/>
                                    </div>                                    
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group" title="Segundo apellido">
                                    <div class="input-group">
                                        <span class="input-group-addon"><i class="icon-user"></i>
                                        </span>
                                        <input type="text" class="form-control" placeholder="Segundo Apellido" required="" maxlength="50" id="txtSegundoApellido" name="segundoApellido" />
                                    </div>                                    
                                </div>
                            </div>
                        </div>                                
                        <div class="row">
                            <div class="col-md-6">
                                <div class="form-group" title="Rol">
                                    <div class="input-group">
                                        <span class="input-group-addon"><i class="icon-user"></i>
                                        </span>
                                        <select class="form-control" id="cmbTipoUsuario" name="consUsuario.idTipoUsuario.idTipoUsuario" ></select>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <div class="input-group" title="Nombre empresa">
                                        <span class="input-group-addon"><i class="icon-user"></i>
                                        </span>
                                        <input type="text" class="form-control"  placeholder="Nombre Empresa" required="" maxlength="50" id="txtNombreEmpresa" name="nombreEmpresa" />
                                        <select class="form-control hidden" id="cmbEmpresa" ></select>
                                    </div>                                    
                                </div>
                            </div>
                        </div>                                                                
                        <div class="row">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <div class="input-group" title="Tipo de documento">
                                        <span class="input-group-addon"><i class="icon-user"></i>
                                        </span>
                                        <select id="cmbTipoDocumento" class="form-control" required="" name="idTipoDocumento.idTipoDocumento" >
                                            <option value="">Seleccione</option>                                                    
                                        </select>
                                    </div>                                            
                                </div>  
                            </div>
                            <div class="col-md-6">
                                <div class="form-group" title="Número de documento">
                                    <div class="input-group">
                                        <span class="input-group-addon"><i class="icon-user"></i>
                                        </span>
                                        <input type="text" class="form-control" placeholder="Número documento" required="" maxlength="30" id="txtNumeroDocumento" name="numeroDocumento" />
                                    </div>                                            
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <div class="input-group" title="Estado">
                                        <span class="input-group-addon"><i class="icon-user"></i>
                                        </span>
                                        <select class="form-control" id="cmbEstado" name="consUsuario.activo">
                                            <option value="">Seleccione</option>
                                            <option value="N">Pendiente</option>
                                            <option value="S">Activo</option>
                                            <option value="I">Inactivo</option>
                                            <option value="B">Bloqueado</option>
                                        </select>
                                    </div>
                                </div>
                            </div>                          
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-success" id="btnGuardar" >Guardar Cambios</button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
                </div>
            </form>
        </div>

    </div>
</div>
<!--FIN MODAL DETALLES/EDITAR USUARIO-->

<script src="../assets/js/views/dashboard/admin/usuarios.js" type="text/javascript"></script>