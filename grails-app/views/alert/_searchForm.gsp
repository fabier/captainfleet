<div class="row">
    <div class="col-md-6">
        <g:form action='index' class="form-horizontal">
            <div class="form-group">
                <label for="name" class="col-md-2 control-label">Nom</label>

                <div class="col-md-8">
                    <input class="form-control" id="name" name="name" placeholder="Recherche"
                           value="${params.name ?: ""}"/>
                </div>

                <div class="col-md-2">
                    <button type="submit" class="btn btn-primary">
                        Rechercher
                    </button>
                </div>
            </div>
        </g:form>
    </div>
</div>