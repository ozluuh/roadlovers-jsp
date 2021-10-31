<%@ attribute name="message" required="true" %>
<%@ attribute name="type" description="Severity of message" %>

<div class="alert alert-dismissible fade show alert-${empty type ? 'info' : type}" role="alert">
    ${message}
    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close">
        <span aria-hidden="true"></span>
    </button>
</div>
