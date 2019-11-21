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
        /*vm.getAffordable = getAffordable;
        vm.deleteBooking = deleteBooking;*/

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

        /*function getAffordable(){
            var url = "/bookings/affordable/" + 100;
            var bookingsPromise = $http.get(url);
            bookingsPromise.then(function(response){
                vm.bookings = response.data;
            });
        }

        function deleteBooking(id){
            var url = "/bookings/delete/" + id;
            $http.post(url).then(function(response){
                vm.bookings = response.data;
            });
        }*/
    }
})();
