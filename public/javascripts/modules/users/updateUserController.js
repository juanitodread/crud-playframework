angular.module("users")
       .controller("updateUserController", UpdateUserController);

function UpdateUserController($scope, $location, $routeParams, usersService) {
  $scope.user = {};
  $scope.updateUser = updateUser;

  getUser($routeParams.id);

  function getUser(id) {
    console.info("getUser");
    usersService.getUser(id).success(function(user) {
      $scope.user = user;
    })
  }

  function updateUser() {
    console.info("updateUser()");
    var newUser = angular.copy($scope.user);
    console.info(newUser);
    usersService.updateUser($scope.user._id.$oid, newUser).success(function() {
      $location.path("/users");
      return;
    });
  }
}
