<%-- 
    Document   : newjsp    
    Author     : jhonjaider1000
--%>

<%@page import="edu.poli.gerencia.votaciones.negocio.utiles.SesionUtil"%>
<%@page import="edu.poli.gerencia.votaciones.modelo.vo.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    if (!SesionUtil.esEmpleado(usuario)) {
        response.sendRedirect("../login/");
    }
%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">                
        <link rel="shortcut icon" href="../assets/ico/favicon.png">

        <title>Dashboard</title>
        <script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
        <script src="../assets/js/global/app.sesion.js" type="text/javascript"></script>
        <!-- Icons -->
        <link href="../assets/css/font-awesome.min.css" rel="stylesheet">
        <link href="../assets/css/simple-line-icons.css" rel="stylesheet">
        <!-- Main styles -->
        <link href="../assets/css/style.css" rel="stylesheet" />
        <link href="../assets/js/libs/sweetalert/dist/sweetalert.css" rel="stylesheet" type="text/css"/>
        <link href="../assets/css/helper-class.css" rel="stylesheet" type="text/css"/>
    </head>   

    <body class="app header-fixed sidebar-fixed aside-menu-fixed aside-menu-hidden">
        <header class="app-header navbar">
            <button class="navbar-toggler mobile-sidebar-toggler hidden-lg-up" type="button">☰</button>
            <a class="navbar-brand" href="#"></a>
            <ul class="nav navbar-nav hidden-md-down b-r-1">
                <li class="nav-item">
                    <a class="nav-link navbar-toggler sidebar-toggler" href="#">☰</a>
                </li>

            </ul>            
            <ul class="nav navbar-nav ml-auto">                
                <li class="nav-item dropdown hidden-md-down">
                    <a class="nav-link nav-pill" data-toggle="dropdown" href="#" role="button" aria-haspopup="true" aria-expanded="false">
                        <i class="icon-bell"></i>
                        <span class="badge badge-pill badge-danger numero_notificaciones hidden">0</span>
                    </a>
                    <div class="dropdown-menu dropdown-menu-right dropdown-menu-lg">

                        <div class="dropdown-header text-center">
                            <strong>Tienes <span class="numero_notificaciones">0</span> notificaciones</strong>
                        </div>

                        <a href="#usuarios" class="dropdown-item">
                            <i class="icon-user-follow text-success"></i> <span id="usuarios_registrados">0</span> Notificaciones
                        </a>                        
                        <a href="#" class="dropdown-item text-center">
                            Ver todo
                        </a>                                                
                    </div>
                </li>                
                <li class="nav-item dropdown " style="padding-right: 30px !important;">
                    <a class="nav-link nav-pill avatar" data-toggle="dropdown" href="#" role="button" aria-haspopup="true" aria-expanded="false">
                        <img src="../assets/img/avatars/usuario.jpg" class="img-avatar" alt="admin@bootstrapmaster.com">                        
                    </a>
                    <div class="dropdown-menu dropdown-menu-right">                        
                        <div class="dropdown-header text-center">
                            <strong>Configuración</strong>
                        </div>
                        <a class="dropdown-item" href="#configuracioncuenta"><i class="fa fa-user"></i> Cuenta</a>                        
                        <a class="dropdown-item" href="../cerrarsesion"><i class="fa fa-lock"></i> Cerrar sesión</a>
                    </div>
                </li>                
            </ul>
        </header>
        <div class="app-body">
            <div class="sidebar">
                <nav class="sidebar-nav">
                    <ul class="nav">
                        <li class="nav-title text-center">
                            <span>Dashboard</span>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="../dashboard/"><i class="icon-speedometer"></i> Dashboard </a>
                        </li>

                        <li class="divider"></li>                        
                    </ul>
                </nav>
            </div>

            <!-- Main content -->
            <main class="main">
                <!-- Breadcrumb -->
                <ol class="breadcrumb mb-0">
                    <li class="breadcrumb-item">Votaciones</li>
                    <li class="breadcrumb-item"><a href="../dashboard/">Dashboard</a></li>
                    <li class="breadcrumb-item active"><span id="itemActual"></span></li>
                </ol>
                <div class="container-fluid" id="contenido">
                    <div class="animated fadeIn" >
                        <div class="card">
                            <div class="card-header">
                                <i class="fa fa-fw fa-home"></i> Inicio
                            </div>
                            <div class="card-block">
                                <h1>Bienvenido!</h1>
                                <p>A continuación se muestran las votaciones actualmente activas en las que puede participar inscribiendose como candidato y/o votando. Haga clic en uno de los siguientes items para ver los detalles de la votación y realizar las respectivas acciones.</p>
                                <hr/>
                                <div class="alert alert-info alert-dismissable" id="alertaConsultandoVotaciones">
                                    <a href="#" class="custom-close close" >&times;</a>
                                    <span id="text"><i class="fa fa-fw fa-refresh fa-spin"></i> Consultando votaciones...</span>
                                </div>
                                <div id="listaVotaciones" class="row">
                                    <div class="col-md-12 hidden model">
                                        <div class="card cursor-pointer m-b-5">
                                            <div class="card-block p-0 clearfix">
                                                <i class="fa fa-check bg-info p-2 font-2xl mr-1 float-left"></i>
                                                <div class="h5 text-info mb-0 pt-1">Sindicato dkdjfkla lkajsdklfjasdklfj...</div>
                                                <div class="text-muted text-uppercase font-weight-bold font-xs">1000 Votos</div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <script src="../assets/js/views/dashboard/generic/listavotaciones.js" type="text/javascript"></script>
                    </div>

                </div>
                <!-- /.conainer-fluid -->
            </main>            

        </div>

        <footer class="app-footer">
            <a href="https://votaciones.com/">Votaciones</a> © 2017.
            <span class="float-right">
                Por <a href="https://grupo.com/">Grupo</a>
            </span>
        </footer>
        <link href="../assets/js/libs/datepicker/datepicker3.css" rel="stylesheet" type="text/css"/>
        <script src="../assets/js/libs/datepicker/bootstrap-datepicker.js" type="text/javascript"></script>
        <a href="../assets/js/libs/datepicker/locales/Spanish.json"></a>

        <script src="../assets/js/libs/dateFunctions.js" type="text/javascript"></script>
        <!-- Bootstrap and necessary plugins -->        
        <script src="../assets/js/libs/tether.min.js"></script>
        <script src="../assets/js/libs/bootstrap.min.js"></script>
        <script src="../assets/js/libs/pace.min.js"></script>


        <!-- Plugins and scripts -->        

        <!-- main scripts -->

        <script src="../assets/js/app.js"></script>





        <!-- Plugins and scripts -->
        <script src="../assets/js/libs/toastr.min.js"></script>
        <!--        <script src="../assets/js/libs/gauge.min.js"></script>-->
        <script src="../assets/js/libs/moment.min.js"></script>
        <script src="../assets/js/libs/jquery.mask.js" type="text/javascript"></script>


        <script src="../assets/js/libs/sweetalert/dist/sweetalert.min.js" type="text/javascript"></script>
        <script src="../assets/js/libs/validator.min.js" type="text/javascript"></script>
        <script src="../assets/js/libs/JJHelpForm.min.js" type="text/javascript"></script>

        <!-- Custom scripts -->
        <!--<script src="../assets/js/views/main.js"></script>-->
        <script src="../assets/js/global/app.dom.js" type="text/javascript"></script>
        <script src="../assets/js/global/app.global.js" type="text/javascript"></script>
        <script src="../assets/js/views/dashboard.js" type="text/javascript"></script>

        <link href="../assets/js/libs/select2/dist/css/select2.min.css" rel="stylesheet" type="text/css"/>
        <script src="../assets/js/libs/select2/dist/js/select2.min.js" type="text/javascript"></script>
        <script src="../assets/js/libs/select2/dist/js/i18n/es.js" type="text/javascript"></script>
        <script src="../assets/js/libs/datatables/jquery.dataTables.min.js" type="text/javascript"></script>
        <script src="../assets/js/libs/datatables/dataTables.bootstrap4.min.js" type="text/javascript"></script>

    </body>   
</html>