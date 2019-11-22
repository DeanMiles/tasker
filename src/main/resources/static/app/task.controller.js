(function () {
    'use strict';

    angular
        .module('app')
        .controller('TaskController', TaskController);

    TaskController.$inject = ['$http'];

    function TaskController($http) {
        var vm = this;

        vm.tasks = [];
        vm.getAll = getAll;

        init();

        function init(){
            getAll();
        }

        function getAll(){
            var url = "/tasks/all";
            var tasksPromise = $http.get(url);
            tasksPromise.then(function(response){
                vm.tasks = response.data;
            });
        }
    }
})();
