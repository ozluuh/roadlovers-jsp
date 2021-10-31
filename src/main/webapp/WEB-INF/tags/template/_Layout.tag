<%@ attribute name="title" required="true" %>
<%@ attribute name="scripts" fragment="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="SiteName" value="Road Lovers" />
<c:url var="bootstrap" value="/lib/bootstrap/dist" />
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <title>${title} | ${SiteName}</title>
        <link rel="stylesheet" href="${bootstrap}/css/bootstrap.min.css" />
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css">
        <link rel="stylesheet" href='<c:url value="/css/site.css" />' />
    </head>
    <body>
        <header>
            <nav class="navbar navbar-expand-sm navbar-dark bg-primary border-bottom box-shadow mb-3">
                <div class="container">
                    <a class="navbar-brand" href='<c:url value="/" />'>${SiteName}</a>
                    <button
                        class="navbar-toggler"
                        type="button"
                        data-bs-toggle="collapse"
                        data-bs-target=".navbar-collapse"
                        aria-controls="navbarSupportedContent"
                        aria-expanded="false"
                        aria-label="Toggle navigation"
                        >
                        <span class="navbar-toggler-icon"></span>
                    </button>
                    <div class="navbar-collapse collapse d-sm-inline-flex justify-content-between">
                        <ul class="navbar-nav flex-grow-1">
                            <li class="nav-item">
                                <a class="nav-link" href='<c:url value="/" />'>Principal</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href='<c:url value="/vehicles" />'>Ve&iacute;culos</a>
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>
        </header>
        <div class="container">
            <main role="main" class="pb-3">
                <jsp:doBody />
            </main>
        </div>

        <footer class="border-top footer text-muted">
            <div class="container">
                &copy; 2021 - ${SiteName}
            </div>
        </footer>
        <script src="${bootstrap}/js/bootstrap.bundle.min.js"></script>
        <c:if test="${not empty scripts}">
            <jsp:invoke fragment="scripts" />
        </c:if>
    </body>
</html>
