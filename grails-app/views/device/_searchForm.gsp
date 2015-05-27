<div class="row">
    <div class="col-md-6">
        <g:form action='index' class="form-horizontal">
            <div class="form-group">
                <div class="col-md-6">
                    <input class="form-control" id="name" name="name" placeholder="Recherche"
                           value="${params.name ?: ""}"/>
                </div>

                <div class="col-md-2">
                    <button type="submit" class="btn btn-primary">
                        <i class="glyphicon glyphicon-search"></i>
                        &nbsp;Rechercher
                    </button>
                </div>
            </div>
        </g:form>
    </div>
</div>