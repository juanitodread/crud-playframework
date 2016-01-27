angular.module("core", [])
       .directive("crudAge", crudAge);

function crudAge() {
    return {
        restrict: "A",
        require: "ngModel",
        link: function(scope, element, attrs, ngModel) {
            scope.$watch(attrs.ngModel, function(newValue, oldValue) {
                var isValid = (newValue > 3 && newValue < 100);
                ngModel.$setValidity(attrs.ngModel, isValid);
            });
        }
    }
}       