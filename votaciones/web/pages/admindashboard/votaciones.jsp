<%@page import="edu.poli.gerencia.votaciones.negocio.utiles.SesionUtil"%>
<%@page import="edu.poli.gerencia.votaciones.modelo.vo.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    if (!SesionUtil.esEmpresa(usuario)) {
        response.sendRedirect("../errors/403.html");
    }
%>
<div class="animated fadeIn">
    <div class="card">
        <div class="card-header">
            <i class="fa fa-edit"></i> Votaciones
        </div>
        <div class="card-block">            
            <div class="display-block">
                <button class="btn btn-primary" id="btnNuevaVotacion" data-toggle="modal" data-target="#modalCogVotacion"><i class="fa fa-fw fa-plus"></i> Nueva</button>
                <a href="#tipovotaciones" class="btn btn-link pull-right candidaight" ><i class="fa fa-fw fa-user"></i> Tipo Votaciones</a>
            </div>
            <hr/>
            <div id="content_tabla" class="">
                <table id="tablaVotaciones" class="table table-striped table-bordered" width="100%"></table>
            </div>            
        </div>
    </div>
</div>

<!--MODAL NUEVA VOTACION-->
<div id="modalCogVotacion" class="modal fade" role="dialog">
    <div class="modal-dialog modal-md">
        <form id="formNuevaVotacion">
            <input type="hidden" id="consVotacion" name="consVotacion" />
            <!-- Modal content-->
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h5 class="modal-title">Acción</h5>
                </div>
                <div class="modal-body">                                
                    <div id="content_nuevo" class="p-l-15 p-r-15">
                        <div class="row">
                            <div class="col-md-12">
                                <div class="form-group">
                                    <div class="alert alert-success alert-dismissable hidden" id="alertaVotaciones">
                                        <a href="#" class="close" >&times;</a>
                                        <ul class="m-b-0"></ul>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="cmbIdTipoVotacion">Tipo votación: (*)</label>
                                    <select id="cmbIdTipoVotacion" class="form-control" required="" name="idTipoVotacion.idTipoVotacion"></select>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label for="txtFechaInicioInscripcion">Fecha inicio inscripción: (*)</label>
                                    <div class="input-group">
                                        <input type="text" class="form-control" id="txtFechaInicioInscripcion" name="fechaInicioInscripcion" required="" data-callback="__dom.formatearFecha" />
                                        <div class="input-group-btn">
                                            <button class="btn btn-default" type="button"><i class="fa fa-fw fa-calendar"></i></button>
                                        </div>
                                    </div>
                                </div>
                            </div>                        
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label for="txtFechaInicioInscripcion">Fecha fin inscripción: (*)</label>
                                    <div class="input-group">
                                        <input type="text" class="form-control" id="txtFechaFinInscripcion" name="fechaFinInscripcion" required="" data-callback="__dom.formatearFecha" />
                                        <div class="input-group-btn">
                                            <button class="btn btn-default" type="button"><i class="fa fa-fw fa-calendar"></i></button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label for="txtFechaInicioVotacion">Fecha inicio votación: (*)</label>
                                    <div class="input-group">
                                        <input type="text" class="form-control" id="txtFechaInicioVotacion" name="fechaInicioVotacion" required="" data-callback="__dom.formatearFecha" />
                                        <div class="input-group-btn">
                                            <button class="btn btn-default" type="button"><i class="fa fa-fw fa-calendar"></i></button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label for="txtFechaFinVotacion">Fecha fin votación: (*)</label>
                                    <div class="input-group">
                                        <input type="text" class="form-control" id="txtFechaFinVotacion" name="fechaFinVotacion" required="" data-callback="__dom.formatearFecha" />
                                        <div class="input-group-btn">
                                            <button class="btn btn-default" type="button"><i class="fa fa-fw fa-calendar"></i></button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label for="cmbEstadoVotacion">Estado:</label>                                    
                                    <select class="form-control" id="cmbEstadoVotacion" name="estadoVotacion">
                                        <option value="S">Selección de candidatos</option>
                                        <option value="E">En curso</option>
                                        <option value="F">Finalizada</option>
                                        <option value="C">Cancelada</option>
                                    </select>                                    
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label for="txtAuthor">Author:</label>
                                    <div class="input-group">
                                        <span class="input-group-addon"><i class="fa fa-fw fa-user"></i></span>
                                        <input type="text" class="form-control no-disabled" id="txtAuthor" name="consUsuarioCreacion.nombreUsuario" disabled=""/>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-12">
                                <div class="form-group">
                                    <button class="btn btn-success" id="btnVerCandidatos"><i class="fa fa-fw fa-list"></i> Ver Candidatos</button>
                                </div>
                            </div>
                        </div>                    
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-success" id="btnGuardar"><i class="fa fa-fw fa-check"></i> Guardar</button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
                </div>
            </div>
        </form>
    </div>
</div>
<!--FIN NUEVA VOTACION-->

<!--MODAL LISTA CANDITADOS-->
<div id="modalListaCandidatos" class="modal fade" role="dialog">
    <div class="modal-dialog" style="max-width: 600px;">
        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h5 class="modal-title"><i class="fa fa-fw fa-list"></i> Lista Candidatos</h5>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-12">
                        <div class="alert alert-success alert-dismissable hidden" id="alertaCandidatos">
                            <a href="#" class="close" >&times;</a>
                            <span id="text"></span>
                        </div>
                        <table id="tablaCandidatos" class="table table-striped table-bordered" width="100%"></table>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal" id="btnCerraModalListaCandidatos">Cerrar</button>
            </div>
        </div>        
    </div>
</div>
<!--FIN LISTA CANDIDATOS-->
<script src="../assets/js/views/dashboard/admin/votaciones.js" type="text/javascript"></script>