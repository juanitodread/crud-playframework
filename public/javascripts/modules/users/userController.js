angular.module("users", [])
       .factory("usersService", UsersService)
       .controller("userController", UserController)
       .constant("usersServiceUrl", "https://crud-lab.herokuapp.com/api/users");

function UsersService($http, usersServiceUrl) {
  function get(param) {
    return request("GET", param);
  }

  function post(data) {
    return request("POST", null, data);
  }

  function put(param, data) {
    return request("PUT", param, data);
  }

  function del(param) {
    return request("DELETE", param);
  }

  function request(verb, param, data) {
    var req = {
      method: verb,
      url: url(param),
      headers: {
        "Access-Control-Allow-Origin": "*"
      },
      data: data
    }
    return $http(req);
  }

  function url(param) {
    if(param == null || !angular.isDefined(param)) {
      param = "";
    } else {
      param = "/" + param;
    }
    return usersServiceUrl + param;
  }

  return {
    getUsers: function() {
      return get();
    },

    getUser: function(id) {
      return get(id);
    },

    saveUser: function(user) {
      return post(user);
    },

    updateUser: function(id, user) {
      return put(id, user);
    },

    deleteUser: function(userId) {
      return del(userId);
    }
  }
}

function UserController($scope, $location, usersService) {
    var selectedId = -1;
    var rings = [];

    $scope.redirectCreateUser = redirectCreateUser;
    $scope.redirectEditUser = redirectEditUser;
    $scope.isBusy = isBusy;
    $scope.isLoading = isLoading;
    $scope.errorMessage = "";
    $scope.cancel = reset;
    $scope.isInReadMode = isInReadMode;
    $scope.isInRemoveMode = isInRemoveMode;
    $scope.startRemove = startRemove;
    $scope.remove = remove;

    $scope.users = [];
    getAllUsers();

    function isBusy(id) {
      if(angular.isDefined(id)) {
        return rings.indexOf(id) >= 0;
      } else {
        return rings.length > 0;
      }
    }

    function busy(id) {
      if(isBusy(id)) {
        return;
      }
      rings.push(id);
    }

    function isLoading() {
      return isBusy(-2);
    }

    function isInReadMode(id) {
      return selectedId < 0 || selectedId != id;
    }

    function isInRemoveMode(id) {
      return selectedId == id && removeFlag;
    }

    function complete(id) {
      var idx = rings.indexOf(id);
      if(idx < 0) {
        return;
      } else {
        rings.splice(idx, 1);
      }
    }

    function reset() {
      selectedId = -1;
      addFlag = false;
      editFlad = false;
      removeFlad = false;
      $scope.errorMessage = "";
    }

    function startRemove(id) {
      reset();
      selectedId = id;
      removeFlag = true;
    }

    function getAllUsers() {
        console.info("getAllUsers()");
        busy(-2);
        usersService.getUsers().success(function(users) {
          $scope.users = users;
          complete(-2);
        }).error(function(errorInfo, status) {
          console.error(errorInfo);
        });
        reset();
    }

    function remove(id) {
      console.info("remove()");
      usersService.deleteUser(id).success(function(users) {
        getAllUsers();
      }).error(function(errorInfo, status) {
        console.error(errorInfo);
      });
      reset();
    }

  function redirectCreateUser() {
    $location.path("/users/create");
    return;
  }

  function redirectEditUser(id) {
    $location.path("/users/edit/" + id);
    return;
  }
}
