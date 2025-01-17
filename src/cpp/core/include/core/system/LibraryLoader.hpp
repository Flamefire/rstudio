/*
 * LibraryLoader.hpp
 *
 * Copyright (C) 2022 by RStudio, PBC
 *
 * Unless you have received this program directly from RStudio pursuant
 * to the terms of a commercial license agreement with RStudio, then
 * this program is licensed to you under the terms of version 3 of the
 * GNU Affero General Public License. This program is distributed WITHOUT
 * ANY EXPRESS OR IMPLIED WARRANTY, INCLUDING THOSE OF NON-INFRINGEMENT,
 * MERCHANTABILITY OR FITNESS FOR A PARTICULAR PURPOSE. Please refer to the
 * AGPL (http://www.gnu.org/licenses/agpl-3.0.txt) for more details.
 *
 */

#ifndef CORE_SYSTEM_LIBRARY_LOADER_HPP
#define CORE_SYSTEM_LIBRARY_LOADER_HPP

#include <string>

#include <boost/noncopyable.hpp>

#include <shared_core/Error.hpp>

#include <core/Log.hpp>

namespace rstudio {
namespace core {

namespace system {

core::Error loadLibrary(const std::string& libPath, void** ppLib);
core::Error verifyLibrary(const std::string& libPath);
core::Error loadSymbol(void* pLib, const std::string& name, void** ppSymbol);
core::Error closeLibrary(void* pLib);

class Library : boost::noncopyable
{
public:

   explicit Library(const std::string& library)
      : pLib_(nullptr)
   {
      core::Error error = loadLibrary(library, &pLib_);
      if (error)
         LOG_ERROR(error);
   }

   ~Library()
   {
      try
      {
         if (pLib_)
         {
            core::Error error = closeLibrary(pLib_);
            if (error)
               LOG_ERROR(error);
         }
      }
      catch (...)
      {

      }
   }

   operator void*() const
   {
      return pLib_;
   }

private:
   void* pLib_;
};



} // namespace system
} // namespace core
} // namespace rstudio

#endif // CORE_SYSTEM_LIBRARY_LOADER_HPP
